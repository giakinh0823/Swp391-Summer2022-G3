CREATE TABLE answer (
  id        int IDENTITY NOT NULL, 
  text      nvarchar(max) NULL, 
  media     nvarchar(2000) NULL, 
  subjectid int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE answer_user (
  userid     int NOT NULL, 
  quizzesid  int NOT NULL, 
  lessonid   int NOT NULL, 
  questionid int NOT NULL, 
  answerid   int NOT NULL, 
  text       nvarchar(max) NULL, 
  media      nvarchar(2000) NULL, 
  is_correct bit NULL, 
  PRIMARY KEY (userid, 
  quizzesid, 
  lessonid, 
  questionid, 
  answerid));
CREATE TABLE customer (
  id       int IDENTITY NOT NULL, 
  fullname nvarchar(255) NOT NULL, 
  email    varchar(255) NOT NULL, 
  gender   bit NOT NULL, 
  phone    varchar(20) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE dimension (
  id          int IDENTITY NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  description nvarchar(2000) NOT NULL, 
  typeid      int NOT NULL, 
  subjectid   int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE feature (
  id      int IDENTITY NOT NULL, 
  name    nvarchar(255) NOT NULL, 
  feature varchar(255) NOT NULL UNIQUE, 
  PRIMARY KEY (id));
CREATE TABLE feature_group (
  featureid int NOT NULL, 
  groupid   int NOT NULL, 
  PRIMARY KEY (featureid, 
  groupid));
CREATE TABLE file_lesson (
  id       int IDENTITY NOT NULL, 
  [file]   varchar(2000) NULL, 
  lessonid int NULL, 
  PRIMARY KEY (id));
CREATE TABLE file_post (
  id     int IDENTITY NOT NULL, 
  [file] varchar(2000) NOT NULL, 
  postid int NULL, 
  PRIMARY KEY (id));
CREATE TABLE file_question (
  id         int IDENTITY NOT NULL, 
  [file]     varchar(2000) NULL, 
  questionid int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE group_user (
  userid  int NOT NULL, 
  groupid int NOT NULL, 
  PRIMARY KEY (userid, 
  groupid));
CREATE TABLE lesson (
  id        int IDENTITY NOT NULL, 
  name      nvarchar(255) NOT NULL, 
  [order]   int NOT NULL, 
  status    bit NOT NULL, 
  video     varchar(255) NULL, 
  content   nvarchar(max) NULL, 
  typeid    int NOT NULL, 
  topicid   int NULL, 
  quizzesid int NULL, 
  subjectid int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE lesson_price_package (
  lessonid        int NOT NULL, 
  price_packageid int NOT NULL, 
  PRIMARY KEY (lessonid, 
  price_packageid));
CREATE TABLE media (
  id         int IDENTITY NOT NULL, 
  type       nvarchar(255) NULL, 
  url        nvarchar(2000) NOT NULL, 
  questionid int NULL, 
  PRIMARY KEY (id));
CREATE TABLE post (
  id          int IDENTITY NOT NULL, 
  title       nvarchar(2000) NOT NULL, 
  thumbnail   varchar(2000) NULL, 
  content     nvarchar(max) NULL, 
  description nvarchar(2000) NULL, 
  featured    bit NOT NULL, 
  flag        bit NOT NULL, 
  status      bit NOT NULL, 
  created_at  datetime NOT NULL, 
  updated_at  datetime NOT NULL, 
  userid      int NOT NULL, 
  categoryId  int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE price_package (
  id          int IDENTITY NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  duration    int NULL, 
  list_price  float(10) NOT NULL, 
  sale_price  float(10) NULL, 
  status      bit NOT NULL, 
  description nvarchar(2000) NULL, 
  subjectid   int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE question (
  id           int IDENTITY NOT NULL, 
  content      nvarchar(max) NOT NULL, 
  content_html nvarchar(max) NULL, 
  explain      nvarchar(max) NULL, 
  status       bit NOT NULL, 
  is_multi     bit NULL, 
  dimensionid  int NOT NULL, 
  userid       int NOT NULL, 
  levelid      int NOT NULL, 
  lessonid     int NOT NULL, 
  subjectid    int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE question_answer (
  answerid   int NOT NULL, 
  questionid int NOT NULL, 
  is_correct bit NULL, 
  PRIMARY KEY (answerid, 
  questionid));
CREATE TABLE question_user (
  userid       int NOT NULL, 
  quizzesid    int NOT NULL, 
  lessonid     int NOT NULL, 
  questionid   int NOT NULL, 
  content      nvarchar(max) NOT NULL, 
  content_html nvarchar(max) NULL, 
  explain      nvarchar(max) NULL, 
  media        varchar(2000) NULL, 
  is_multi     bit NULL, 
  dimensionid  int NOT NULL, 
  levelid      int NOT NULL, 
  PRIMARY KEY (userid, 
  quizzesid, 
  lessonid, 
  questionid));
CREATE TABLE quizzes (
  id          int IDENTITY NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  duration    int NOT NULL, 
  pass_rate   int NOT NULL, 
  description nvarchar(2000) NULL, 
  subjectid   int NOT NULL, 
  typeid      int NOT NULL, 
  levelid     int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE quizzes_user (
  quizzesid   int NOT NULL, 
  userid      int NOT NULL, 
  lessonid    int NOT NULL, 
  levelid     int NOT NULL, 
  typeid      int NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  duration    int NOT NULL, 
  pass_rate   int NOT NULL, 
  description nvarchar(2000) NULL, 
  start_time  datetime NULL, 
  end_time    datetime NULL, 
  PRIMARY KEY (quizzesid, 
  userid, 
  lessonid));
CREATE TABLE registration (
  id              int IDENTITY NOT NULL, 
  status          bit NOT NULL, 
  total_cost      float(10) NOT NULL, 
  valid_from      datetime NOT NULL, 
  valid_to        datetime NOT NULL, 
  created_at      datetime NOT NULL, 
  subjectid       int NOT NULL, 
  userid          int NULL, 
  customerid      int NULL, 
  update_by       int NULL, 
  price_packageid int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE setting (
  id          int IDENTITY NOT NULL, 
  type        nvarchar(255) NOT NULL, 
  value       nvarchar(255) NOT NULL, 
  [order]     int NOT NULL, 
  status      bit NOT NULL, 
  description varchar(2000) NULL, 
  parent      int NULL, 
  PRIMARY KEY (id));
CREATE TABLE setting_dimension_quizzes (
  settingid       int NOT NULL, 
  dimensionid     int NOT NULL, 
  number_question int NULL, 
  PRIMARY KEY (settingid, 
  dimensionid));
CREATE TABLE setting_quizzes (
  id             int IDENTITY NOT NULL, 
  total_question int NOT NULL, 
  quizzesid      int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE slider (
  id       int IDENTITY NOT NULL, 
  title    nvarchar(255) NOT NULL, 
  image    varchar(255) NULL, 
  backlink varchar(255) NULL, 
  status   bit NOT NULL, 
  note     nvarchar(max) NULL, 
  PRIMARY KEY (id));
CREATE TABLE subject (
  id          int IDENTITY NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  description nvarchar(2000) NULL, 
  image       varchar(max) NULL, 
  status      bit NOT NULL, 
  featured    bit NULL, 
  created_at  datetime NULL, 
  updated_at  datetime NULL, 
  categoryid  int NOT NULL, 
  userid      int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE [user] (
  id        int IDENTITY NOT NULL, 
  username  varchar(255) NOT NULL UNIQUE, 
  password  varchar(255) NOT NULL, 
  email     varchar(255) NOT NULL UNIQUE, 
  phone     varchar(20) NOT NULL, 
  fullname  nvarchar(255) NOT NULL, 
  gender    bit NOT NULL, 
  is_super  bit NOT NULL, 
  create_at datetime NULL, 
  PRIMARY KEY (id));
CREATE TABLE user_choose (
  userid     int NOT NULL, 
  quizzesid  int NOT NULL, 
  lessonid   int NOT NULL, 
  questionid int NOT NULL, 
  answerid   int NOT NULL, 
  created_at datetime NULL, 
  PRIMARY KEY (userid, 
  quizzesid, 
  lessonid, 
  questionid, 
  answerid));
CREATE TABLE user_lesson (
  created_at datetime NULL, 
  userid     int NOT NULL, 
  lessonid   int NOT NULL, 
  PRIMARY KEY (userid, 
  lessonid));
CREATE INDEX answer_id 
  ON answer (id);
CREATE INDEX customer_id 
  ON customer (id);
CREATE INDEX dimension_id 
  ON dimension (id);
CREATE INDEX feature_id 
  ON feature (id);
CREATE INDEX file_lesson_id 
  ON file_lesson (id);
CREATE INDEX file_post_id 
  ON file_post (id);
CREATE INDEX lesson_id 
  ON lesson (id);
CREATE INDEX media_id 
  ON media (id);
CREATE INDEX post_id 
  ON post (id);
CREATE INDEX price_package_id 
  ON price_package (id);
CREATE INDEX question_id 
  ON question (id);
CREATE INDEX quizzes_id 
  ON quizzes (id);
CREATE INDEX registration_id 
  ON registration (id);
CREATE INDEX setting_id 
  ON setting (id);
CREATE INDEX setting_quizzes_id 
  ON setting_quizzes (id);
CREATE INDEX slider_id 
  ON slider (id);
CREATE INDEX subject_id 
  ON subject (id);
CREATE INDEX user_id 
  ON [user] (id);
ALTER TABLE question_answer ADD CONSTRAINT answer_question_answer FOREIGN KEY (answerid) REFERENCES answer (id);
ALTER TABLE answer_user ADD CONSTRAINT answer_user_answer FOREIGN KEY (answerid) REFERENCES answer (id);
ALTER TABLE user_choose ADD CONSTRAINT answer_user_user_choose FOREIGN KEY (userid, quizzesid, lessonid, questionid, answerid) REFERENCES answer_user (userid, quizzesid, lessonid, questionid, answerid);
ALTER TABLE registration ADD CONSTRAINT customer_registration FOREIGN KEY (customerid) REFERENCES [customer    ] (id);
ALTER TABLE question ADD CONSTRAINT dimension_question FOREIGN KEY (dimensionid) REFERENCES [dimension ] (id);
ALTER TABLE setting_dimension_quizzes ADD CONSTRAINT dimension_setting_dimension_quizzes FOREIGN KEY (dimensionid) REFERENCES [dimension ] (id);
ALTER TABLE question_user ADD CONSTRAINT dimension_user_question FOREIGN KEY (dimensionid) REFERENCES [dimension ] (id);
ALTER TABLE feature_group ADD CONSTRAINT group_feature FOREIGN KEY (featureid) REFERENCES feature (id) ON DELETE Cascade;
ALTER TABLE file_lesson ADD CONSTRAINT lesson_file_lesson FOREIGN KEY (lessonid) REFERENCES lesson (id) ON DELETE Cascade;
ALTER TABLE lesson_price_package ADD CONSTRAINT lesson_lesson_price_package FOREIGN KEY (lessonid) REFERENCES lesson (id) ON DELETE Cascade;
ALTER TABLE question ADD CONSTRAINT lesson_question FOREIGN KEY (lessonid) REFERENCES lesson (id);
ALTER TABLE quizzes_user ADD CONSTRAINT lesson_quizzes_user FOREIGN KEY (lessonid) REFERENCES lesson (id);
ALTER TABLE lesson ADD CONSTRAINT lesson_subject FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE Cascade;
ALTER TABLE user_lesson ADD CONSTRAINT lesson_user_lesson FOREIGN KEY (lessonid) REFERENCES lesson (id);
ALTER TABLE quizzes_user ADD CONSTRAINT level_quizzes_user FOREIGN KEY (levelid) REFERENCES setting (id);
ALTER TABLE question_user ADD CONSTRAINT level_user_question FOREIGN KEY (levelid) REFERENCES setting (id);
ALTER TABLE setting ADD CONSTRAINT parent_setting FOREIGN KEY (parent) REFERENCES setting (id);
ALTER TABLE file_post ADD CONSTRAINT post_file FOREIGN KEY (postid) REFERENCES post (id) ON DELETE Cascade;
ALTER TABLE lesson_price_package ADD CONSTRAINT price_package_lesson FOREIGN KEY (price_packageid) REFERENCES price_package (id);
ALTER TABLE registration ADD CONSTRAINT price_package_registration FOREIGN KEY (price_packageid) REFERENCES price_package (id);
ALTER TABLE file_question ADD CONSTRAINT question_file_question FOREIGN KEY (questionid) REFERENCES question (id);
ALTER TABLE question_user ADD CONSTRAINT question_user_question FOREIGN KEY (questionid) REFERENCES question (id);
ALTER TABLE user_choose ADD CONSTRAINT question_user_user_choose FOREIGN KEY (userid, quizzesid, lessonid, questionid) REFERENCES question_user (userid, quizzesid, lessonid, questionid);
ALTER TABLE lesson ADD CONSTRAINT quizzes_lesson FOREIGN KEY (quizzesid) REFERENCES quizzes (id);
ALTER TABLE quizzes_user ADD CONSTRAINT quizzes_quizzes_user FOREIGN KEY (quizzesid) REFERENCES quizzes (id);
ALTER TABLE setting_quizzes ADD CONSTRAINT quizzes_setting FOREIGN KEY (quizzesid) REFERENCES quizzes (id) ON DELETE Cascade;
ALTER TABLE question_user ADD CONSTRAINT quizzes_user_question_user FOREIGN KEY (quizzesid, userid, lessonid) REFERENCES quizzes_user (quizzesid, userid, lessonid);
ALTER TABLE quizzes_user ADD CONSTRAINT quizzes_user_quizzes FOREIGN KEY (userid) REFERENCES [user] (id);
ALTER TABLE post ADD CONSTRAINT setting_category_post FOREIGN KEY (categoryId) REFERENCES setting (id) ON DELETE Cascade;
ALTER TABLE subject ADD CONSTRAINT setting_category_subject FOREIGN KEY (categoryid) REFERENCES setting (id);
ALTER TABLE feature_group ADD CONSTRAINT setting_feature_group FOREIGN KEY (groupid) REFERENCES setting (id) ON DELETE Cascade;
ALTER TABLE group_user ADD CONSTRAINT setting_group_user FOREIGN KEY (groupid) REFERENCES setting (id) ON DELETE Cascade;
ALTER TABLE question ADD CONSTRAINT setting_level_question FOREIGN KEY (levelid) REFERENCES setting (id) ON DELETE Cascade;
ALTER TABLE quizzes ADD CONSTRAINT setting_level_quizzes FOREIGN KEY (levelid) REFERENCES setting (id);
ALTER TABLE setting_dimension_quizzes ADD CONSTRAINT setting_quizzes_setting_dimension_quizzes FOREIGN KEY (settingid) REFERENCES setting_quizzes (id) ON DELETE Cascade;
ALTER TABLE [dimension ] ADD CONSTRAINT setting_type_dimension FOREIGN KEY (typeid) REFERENCES setting (id);
ALTER TABLE lesson ADD CONSTRAINT setting_type_lesson FOREIGN KEY (typeid) REFERENCES setting (id);
ALTER TABLE quizzes ADD CONSTRAINT setting_type_quizzes FOREIGN KEY (typeid) REFERENCES setting (id) ON DELETE Cascade;
ALTER TABLE answer ADD CONSTRAINT subject_answer FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE Cascade;
ALTER TABLE [dimension ] ADD CONSTRAINT subject_dimension FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE Cascade;
ALTER TABLE price_package ADD CONSTRAINT subject_price_package FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE Cascade;
ALTER TABLE question ADD CONSTRAINT subject_question FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE Cascade;
ALTER TABLE quizzes ADD CONSTRAINT subject_quizzes FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE Cascade;
ALTER TABLE registration ADD CONSTRAINT subject_registration FOREIGN KEY (subjectid) REFERENCES subject (id);
ALTER TABLE lesson ADD CONSTRAINT topic_lesson FOREIGN KEY (topicid) REFERENCES lesson (id);
ALTER TABLE quizzes_user ADD CONSTRAINT type_quizzes_user FOREIGN KEY (typeid) REFERENCES setting (id);
ALTER TABLE registration ADD CONSTRAINT update_by FOREIGN KEY (update_by) REFERENCES [user] (id);
ALTER TABLE group_user ADD CONSTRAINT user_group FOREIGN KEY (userid) REFERENCES [user] (id) ON DELETE Cascade;
ALTER TABLE post ADD CONSTRAINT user_post FOREIGN KEY (userid) REFERENCES [user] (id) ON DELETE Cascade;
ALTER TABLE question ADD CONSTRAINT user_question FOREIGN KEY (userid) REFERENCES [user] (id);
ALTER TABLE answer_user ADD CONSTRAINT user_question_user_answer FOREIGN KEY (userid, quizzesid, lessonid, questionid) REFERENCES question_user (userid, quizzesid, lessonid, questionid);
ALTER TABLE registration ADD CONSTRAINT user_registration FOREIGN KEY (userid) REFERENCES [user] (id) ON DELETE Cascade;
ALTER TABLE subject ADD CONSTRAINT user_subject FOREIGN KEY (userid) REFERENCES [user] (id);
ALTER TABLE user_lesson ADD CONSTRAINT user_user_lesson FOREIGN KEY (userid) REFERENCES [user] (id) ON DELETE Cascade;
