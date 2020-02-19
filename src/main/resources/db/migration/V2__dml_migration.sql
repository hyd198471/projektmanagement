--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1 (Debian 12.1-1.pgdg100+1)
-- Dumped by pg_dump version 12.0

-- Started on 2020-02-19 16:19:25

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

--
-- TOC entry 2985 (class 0 OID 16527)
-- Dependencies: 209
-- Data for Name: organisation; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.organisation (id, display_name) FROM stdin;
\.


--
-- TOC entry 2986 (class 0 OID 16532)
-- Dependencies: 210
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.permission (id, display_name) FROM stdin;
\.


--
-- TOC entry 2982 (class 0 OID 16509)
-- Dependencies: 206
-- Data for Name: subject; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.subject (id, email, type, username, subject_id) FROM stdin;
\.


--
-- TOC entry 2983 (class 0 OID 16517)
-- Dependencies: 207
-- Data for Name: project; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.project (id, display_name, parent_id, project_id, subprojects_order) FROM stdin;
\.


--
-- TOC entry 2984 (class 0 OID 16522)
-- Dependencies: 208
-- Data for Name: project_membership; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.project_membership (id, project_membership_id) FROM stdin;
\.


--
-- TOC entry 2980 (class 0 OID 16482)
-- Dependencies: 204
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.role (id, display_name, role_id) FROM stdin;
51	admin	\N
52	customer	\N
53	developer	\N
54	ops	\N
251	team_lead	\N
\.


--
-- TOC entry 2988 (class 0 OID 16542)
-- Dependencies: 212
-- Data for Name: project_membership_role; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.project_membership_role (project_membership_id, role_id) FROM stdin;
\.


--
-- TOC entry 2987 (class 0 OID 16537)
-- Dependencies: 211
-- Data for Name: role_permission; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.role_permission (role_id, permission_id) FROM stdin;
\.


--
-- TOC entry 2978 (class 0 OID 16397)
-- Dependencies: 202
-- Data for Name: sequence; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.sequence (seq_name, seq_count) FROM stdin;
SEQ_GEN	500
\.


--
-- TOC entry 2979 (class 0 OID 16472)
-- Dependencies: 203
-- Data for Name: user_; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.user_ (id, encodedpassword, lastlogindate, username) FROM stdin;
56	$31$16$RMy90VqBCh6OhSYqLvdXDS8qsKaE8-kk8wzQIr2znSE	1581607597410	yuandong.hu@solve_it_mvi.com
101	$31$16$YPyFDMk2xCfS9GsgANMhtJTNwE6PFNR-BlvJEiRW0LQ	1581671398782	yuandong.hu@gmail.com
201	$31$16$FFsUspJfeRzaW1IypwkvxY7qKFAyFM38J4GNuxCFAcs	1581697212804	yuandong.hu.mvi1@gmail.com
301	$31$16$9Tqg-Wqd32f0MaVPjNpnpyaOcZW02Zvz4XUhL-glDHo	1581934803375	yuandong.hu.mvi2@gmail.com
55	$31$16$9qIw4IMc5cAHJroFrGzpklNECJ2FWXEokyiMd3i7K-0	1582033528321	huyuandong@hotmail.com
\.


--
-- TOC entry 2981 (class 0 OID 16487)
-- Dependencies: 205
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: pm
--

COPY public.user_role (user_id, role_id) FROM stdin;
55	51
56	52
56	53
101	52
101	53
201	51
201	53
301	53
301	54
\.


-- Completed on 2020-02-19 16:19:25

--
-- PostgreSQL database dump complete
--

