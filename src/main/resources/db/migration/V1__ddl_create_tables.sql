--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1 (Debian 12.1-1.pgdg100+1)
-- Dumped by pg_dump version 12.0

-- Started on 2020-02-19 16:13:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 16527)
-- Name: organisation; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.organisation (
    id bigint NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.organisation OWNER TO pm;

--
-- TOC entry 210 (class 1259 OID 16532)
-- Name: permission; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.permission (
    id bigint NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.permission OWNER TO pm;

--
-- TOC entry 207 (class 1259 OID 16517)
-- Name: project; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.project (
    id bigint NOT NULL,
    display_name character varying(255),
    parent_id bigint,
    project_id bigint,
    subprojects_order integer
);


ALTER TABLE public.project OWNER TO pm;

--
-- TOC entry 208 (class 1259 OID 16522)
-- Name: project_membership; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.project_membership (
    id bigint NOT NULL,
    project_membership_id bigint
);


ALTER TABLE public.project_membership OWNER TO pm;

--
-- TOC entry 212 (class 1259 OID 16542)
-- Name: project_membership_role; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.project_membership_role (
    project_membership_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.project_membership_role OWNER TO pm;

--
-- TOC entry 204 (class 1259 OID 16482)
-- Name: role; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    display_name character varying(255),
    role_id bigint
);


ALTER TABLE public.role OWNER TO pm;

--
-- TOC entry 211 (class 1259 OID 16537)
-- Name: role_permission; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.role_permission (
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL
);


ALTER TABLE public.role_permission OWNER TO pm;

--
-- TOC entry 202 (class 1259 OID 16397)
-- Name: sequence; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO pm;

--
-- TOC entry 206 (class 1259 OID 16509)
-- Name: subject; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.subject (
    id bigint NOT NULL,
    email character varying(255),
    type integer,
    username character varying(255),
    subject_id bigint
);


ALTER TABLE public.subject OWNER TO pm;

--
-- TOC entry 203 (class 1259 OID 16472)
-- Name: user_; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.user_ (
    id bigint NOT NULL,
    encodedpassword character varying(255),
    lastlogindate bigint,
    username character varying(255)
);


ALTER TABLE public.user_ OWNER TO pm;

--
-- TOC entry 205 (class 1259 OID 16487)
-- Name: user_role; Type: TABLE; Schema: public; Owner: pm
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_role OWNER TO pm;

--
-- TOC entry 2834 (class 2606 OID 16531)
-- Name: organisation organisation_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT organisation_pkey PRIMARY KEY (id);


--
-- TOC entry 2836 (class 2606 OID 16536)
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- TOC entry 2832 (class 2606 OID 16526)
-- Name: project_membership project_membership_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project_membership
    ADD CONSTRAINT project_membership_pkey PRIMARY KEY (id);


--
-- TOC entry 2840 (class 2606 OID 16546)
-- Name: project_membership_role project_membership_role_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project_membership_role
    ADD CONSTRAINT project_membership_role_pkey PRIMARY KEY (project_membership_id, role_id);


--
-- TOC entry 2830 (class 2606 OID 16521)
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- TOC entry 2838 (class 2606 OID 16541)
-- Name: role_permission role_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (role_id, permission_id);


--
-- TOC entry 2824 (class 2606 OID 16486)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2818 (class 2606 OID 16401)
-- Name: sequence sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- TOC entry 2828 (class 2606 OID 16516)
-- Name: subject subject_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_pkey PRIMARY KEY (id);


--
-- TOC entry 2820 (class 2606 OID 16479)
-- Name: user_ user__pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__pkey PRIMARY KEY (id);


--
-- TOC entry 2822 (class 2606 OID 16481)
-- Name: user_ user__username_key; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__username_key UNIQUE (username);


--
-- TOC entry 2826 (class 2606 OID 16491)
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2847 (class 2606 OID 16567)
-- Name: project_membership fk_project_membership_project_membership_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project_membership
    ADD CONSTRAINT fk_project_membership_project_membership_id FOREIGN KEY (project_membership_id) REFERENCES public.subject(id);


--
-- TOC entry 2850 (class 2606 OID 16582)
-- Name: project_membership_role fk_project_membership_role_project_membership_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project_membership_role
    ADD CONSTRAINT fk_project_membership_role_project_membership_id FOREIGN KEY (project_membership_id) REFERENCES public.project_membership(id);


--
-- TOC entry 2851 (class 2606 OID 16587)
-- Name: project_membership_role fk_project_membership_role_role_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project_membership_role
    ADD CONSTRAINT fk_project_membership_role_role_id FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 2845 (class 2606 OID 16552)
-- Name: project fk_project_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fk_project_parent_id FOREIGN KEY (parent_id) REFERENCES public.project(id);


--
-- TOC entry 2846 (class 2606 OID 16557)
-- Name: project fk_project_project_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fk_project_project_id FOREIGN KEY (project_id) REFERENCES public.subject(id);


--
-- TOC entry 2848 (class 2606 OID 16572)
-- Name: role_permission fk_role_permission_permission_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT fk_role_permission_permission_id FOREIGN KEY (permission_id) REFERENCES public.permission(id);


--
-- TOC entry 2849 (class 2606 OID 16577)
-- Name: role_permission fk_role_permission_role_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT fk_role_permission_role_id FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 2841 (class 2606 OID 16562)
-- Name: role fk_role_role_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT fk_role_role_id FOREIGN KEY (role_id) REFERENCES public.subject(id);


--
-- TOC entry 2844 (class 2606 OID 16547)
-- Name: subject fk_subject_subject_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT fk_subject_subject_id FOREIGN KEY (subject_id) REFERENCES public.organisation(id);


--
-- TOC entry 2843 (class 2606 OID 16497)
-- Name: user_role fk_user_role_role_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 2842 (class 2606 OID 16492)
-- Name: user_role fk_user_role_user_id; Type: FK CONSTRAINT; Schema: public; Owner: pm
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES public.user_(id);


-- Completed on 2020-02-19 16:13:42

--
-- PostgreSQL database dump complete
--

