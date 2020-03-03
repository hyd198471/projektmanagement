ALTER TABLE ONLY public.project
    ADD CONSTRAINT user__displayname_key UNIQUE (display_name);