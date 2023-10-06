--liquibase formatted sql
--changeset zulvan:22.2.03

INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_ACM'), (select id from app_accessibility where code = 'APT_VIEW'));
INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_ACM'), (select id from app_accessibility where code = 'APT_MODIFY'));
INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_ACM'), (select id from app_accessibility where code = 'HST_VIEW'));
INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_ACM'), (select id from app_accessibility where code = 'VIDCAL'));
INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_SPV'), (select id from app_accessibility where code = 'APT_VIEW_CHANNEL'));
INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_SPV'), (select id from app_accessibility where code = 'HST_VIEW_CHANNEL'));
INSERT INTO role_accessibility(role_id, accessibility_id)
VALUES((select id from app_role where code = 'MAXI_SPV'), (select id from app_accessibility where code = 'ADT_VIEW'));
