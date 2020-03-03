drop table if exists public.organisation cascade;
drop table if exists public.project cascade;
drop table if exists public.role cascade;
drop table if exists public.subject cascade;
drop table if exists public.user_ cascade;
drop table if exists public.user_role cascade;
drop table if exists public.user_project cascade;

CREATE TABLE public.organisation (
    id bigint NOT NULL,
    display_name character varying(255)
);

CREATE TABLE public.project (
    id bigint NOT NULL,
    display_name character varying(255),
    parent_id bigint,
    sub_projects_position integer,
    organisation_id bigint
);

CREATE TABLE public.role (
    id bigint NOT NULL,
    display_name character varying(255)
);

CREATE TABLE public.user_ (
    id bigint NOT NULL,
    encodedpassword character varying(255),
    lastlogindate bigint,
    username character varying(255),
    type character varying(255),
    organisation_id bigint
);

CREATE TABLE public.user_project (
    user_id bigint NOT NULL,
    project_id bigint NOT NULL
);

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT organisation_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__username_key UNIQUE (username);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT fk_user_organisation_id FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fk_project_parent_id FOREIGN KEY (parent_id) REFERENCES public.project(id);

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fk_project_organisation_id FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES public.role(id);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES public.user_(id);

ALTER TABLE ONLY public.user_project
    ADD CONSTRAINT fk_user_project_user_id FOREIGN KEY (user_id) REFERENCES public.user_(id);

ALTER TABLE ONLY public.user_project
    ADD CONSTRAINT fk_user_project_project_id FOREIGN KEY (project_id) REFERENCES public.project(id);

ALTER TABLE ONLY public.user_project
    ADD CONSTRAINT user_project_pkey PRIMARY KEY (user_id, project_id);