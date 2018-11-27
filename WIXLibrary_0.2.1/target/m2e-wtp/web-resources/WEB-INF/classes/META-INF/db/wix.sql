DROP TABLE IF EXISTS wix_file_entry;
DROP TABLE IF EXISTS wix_file_version;
DROP TABLE IF EXISTS wix_file_tree;
DROP TABLE IF EXISTS wix_file_meta_info;
DROP TABLE IF EXISTS wix_wordtank_meta_info;
DROP TABLE IF EXISTS wix_wordtank;
DROP TABLE IF EXISTS wix_synonym_word;
DROP TABLE IF EXISTS wix_synonym_group;

CREATE TABLE wix_file_meta_info (
    wid integer,
    name varchar NOT NULL,
    author varchar NOT NULL,
    comment varchar,
    description varchar,
    language varchar,
    type integer DEFAULT 0,
    PRIMARY KEY (wid),
    UNIQUE (name, author)
);

CREATE TABLE wix_file_tree (
    parent integer REFERENCES wix_file_meta_info(wid),
    child integer REFERENCES wix_file_meta_info(wid),
    PRIMARY KEY (parent, child)
);

CREATE TABLE wix_file_version (
    wid integer REFERENCES wix_file_meta_info(wid),
    version integer DEFAULT 1,
    revision_num varchar,
    PRIMARY KEY (wid, version)
);

CREATE TABLE wix_file_entry (
    wid integer REFERENCES wix_file_meta_info(wid) NOT NULL,
    eid integer NOT NULL,
    keyword varchar NOT NULL,
    target varchar NOT NULL,
    click_count integer DEFAULT 0,
    PRIMARY KEY (wid, eid)
);

-- word tank用のid, wordtank name, path
CREATE TABLE wix_wordtank_meta_info (
	id SERIAL,
	wordtank_name varchar,
	path varchar,
	PRIMARY KEY (id)
);

INSERT INTO wix_wordtank_meta_info(wordtank_name, path) VALUES('google', 'https://www.google.co.jp/?#q=');
INSERT INTO wix_wordtank_meta_info(wordtank_name, path) VALUES('twitter', 'https://twitter.com/search?q=');
INSERT INTO wix_wordtank_meta_info(wordtank_name, path) VALUES('amazon', 'http://www.amazon.co.jp/s/ref=nb_sb_noss_2?field-keywords=');
INSERT INTO wix_wordtank_meta_info(wordtank_name, path) VALUES('yahoo', 'http://search.yahoo.co.jp/search?p=');
INSERT INTO wix_wordtank_meta_info(wordtank_name, path) VALUES('youtube', 'http://www.youtube.com/results?search_query=');

-- word tank用keywordテーブル
CREATE TABLE wix_wordtank (
	id SERIAL,
	keyword varchar,
	clickCount integer DEFAULT 0,
	PRIMARY KEY (id)
);

CREATE TABLE category_type (
	tid SERIAL,
	name varchar NOT NULL,
	PRIMARY KEY (tid)
);

INSERT INTO category_type (name) VALUES ('About keyword');
INSERT INTO category_type (name) VALUES ('About target');

CREATE TABLE category (
	cid SERIAL,
	name varchar NOT NULL,
	type int REFERENCES category_type(tid),
	PRIMARY KEY (cid)
);


INSERT INTO category (name, type) VALUES ('Wikipedia', 2);
INSERT INTO category (name, type) VALUES ('Dictionary site', 2);
INSERT INTO category (name, type) VALUES ('Corporate site', 2);
INSERT INTO category (name, type) VALUES ('Promotion site', 2);
INSERT INTO category (name, type) VALUES ('Web service site', 2);
INSERT INTO category (name, type) VALUES ('EC site', 2);
INSERT INTO category (name, type) VALUES ('Official website', 2);
INSERT INTO category (name, type) VALUES ('Blog site', 2);

INSERT INTO category (name, type) VALUES ('Entertainment', 1);
INSERT INTO category (name, type) VALUES ('Sports', 1);
INSERT INTO category (name, type) VALUES ('Education', 1);
INSERT INTO category (name, type) VALUES ('Technology', 1);
INSERT INTO category (name, type) VALUES ('Life', 1);
INSERT INTO category (name, type) VALUES ('Society', 1);
INSERT INTO category (name, type) VALUES ('Government', 1);

CREATE TABLE category_wix (
	cid int NOT NULL REFERENCES category(cid),
	wid int NOT NULL REFERENCES wix_file_meta_info(wid),
	PRIMARY KEY (cid, wid)
);

CREATE TABLE wix_synonym_word (
	id SERIAL,
	keyword varchar NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE wix_synonym_group (
	id int,
	g_number int REFERENCES wix_synonym_word(id)
);