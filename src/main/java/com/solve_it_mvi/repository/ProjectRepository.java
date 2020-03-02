package com.solve_it_mvi.repository;

import com.solve_it_mvi.model.Project;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class ProjectRepository extends AbstractRepository {

    @Transactional(REQUIRED)
    public void create(JsonObject jsonObject) {
        Project project = new Project();

        String displayName = jsonObject.getString("displayName");
        JsonValue parentId = jsonObject.get("parentId");

        project.setDisplayName(displayName);
        if(parentId == null) {
            project.setParent(null);
        } else {
            int parentIdInt = parentId.asJsonObject().getInt("parentId");
            Project existedParentProject = em.find(Project.class, parentIdInt);
            project.setParent(existedParentProject);
        }

        project.setSubProjects(getSubProjects(project, jsonObject));

        em.persist(jsonObject);
    }

    private List<Project> getSubProjects(Project project, JsonObject jsonObject) {
        JsonArray subProjectJsons = jsonObject.getJsonArray("subProjects");
        List<Project> subProjects = new LinkedList<>();
        for (int i = 0; i < subProjectJsons.size(); i++) {
            JsonObject subProjectJson = subProjectJsons.getJsonObject(i);
            String subDisplayName = subProjectJson.getString("displayName");
            JsonArray subProjectArray= subProjectJson.getJsonArray("subProjects");
            Project newProject = new Project();
            newProject.setDisplayName(subDisplayName);
            newProject.setParent(project);
            List<Project> subProjects1 = !subProjectArray.isEmpty() ? getSubProjects(newProject, subProjectJson) : new LinkedList<>();
            newProject.setSubProjects(subProjects1);
            subProjects.add(newProject);
        }
        return subProjects;
    }

    public List<Project> findProjectsByIds(Set<Long> ids) {
        Query query = em.createQuery("select p from project p where p.id in :ids");
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}
