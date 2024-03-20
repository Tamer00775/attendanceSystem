DO
$$
DECLARE
r RECORD;
BEGIN
FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
END LOOP;
END
$$

DROP TABLE IF EXISTS public.attendance_info CASCADE;
DROP TABLE IF EXISTS public.attendance_record CASCADE;
DROP TABLE IF EXISTS public.check_in_for_session CASCADE;
DROP TABLE IF EXISTS public.class_room CASCADE;
DROP TABLE IF EXISTS public.course CASCADE;
DROP TABLE IF EXISTS public.databasechangelog CASCADE;
DROP TABLE IF EXISTS public.databasechangeloglock CASCADE;
DROP TABLE IF EXISTS public.person CASCADE;
DROP TABLE IF EXISTS public.person_auth CASCADE;
DROP TABLE IF EXISTS public.person_info CASCADE;
DROP TABLE IF EXISTS public.person_role CASCADE;
DROP TABLE IF EXISTS public.reason_for_absence CASCADE;
DROP TABLE IF EXISTS public.role CASCADE;
DROP TABLE IF EXISTS public.schedule CASCADE;
DROP TABLE IF EXISTS public.secret_code_for_check_in CASCADE;
DROP TABLE IF EXISTS public.section CASCADE;
DROP TABLE IF EXISTS public.section_person CASCADE;
DROP TABLE IF EXISTS public.speciality CASCADE;
