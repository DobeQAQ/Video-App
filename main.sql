/*
 Navicat Premium Data Transfer

 Source Server         : Group4
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 21/05/2021 23:58:23
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for android_metadata
-- ----------------------------
DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE "android_metadata" (
  "locale" TEXT
);

-- ----------------------------
-- Records of android_metadata
-- ----------------------------
INSERT INTO "android_metadata" VALUES ('en_US');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS "category";
CREATE TABLE "category" (
  "category_id" INTEGER NOT NULL,
  "category_name" TEXT,
  PRIMARY KEY ("category_id")
);

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO "category" VALUES (1, '测试');
INSERT INTO "category" VALUES (2, '数据库');
INSERT INTO "category" VALUES (3, 'Algorithm');
INSERT INTO "category" VALUES (4, 'C++');
INSERT INTO "category" VALUES (5, '英语词汇');
INSERT INTO "category" VALUES (6, 'Mysql');
INSERT INTO "category" VALUES (7, 'Java');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS "news";
CREATE TABLE "news" (
  "news_id" INTEGER NOT NULL,
  "news_title" TEXT,
  "author_name" TEXT,
  "header_url" TEXT,
  "release_date" TEXT,
  "news_content" TEXT,
  PRIMARY KEY ("news_id")
);

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO "news" VALUES (1, '1《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2020-07-31 22:23:00', 'Frankie Liu

一只大三计算机小菜鸡。 ≖‿≖

算法一般，技术蒟蒻。╥﹏╥

此博客一在锻炼自己，二在记录自己平时遇到的问题和解决方法。博客会一直完善下去，有问题欢迎联系我。');
INSERT INTO "news" VALUES (2, '2《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2020-08-31 22:23:00', '#include<bits/stdc++.h>
#define PI acos(-1)
using namespace std;
typedef long long ll;
inline ll read() {
	ll x=0,f=1;
	char ch=getchar();
	while(ch<''0''||ch>''9'') {
		if(ch==''-'') f=-1;
		ch=getchar();
	}
	while(ch>=''0''&&ch<=''9'') {
		x=(x<<1)+(x<<3)+ch-''0'';
		ch=getchar();
	}
	return x*f;
}
const int maxn=1e6+5;
const ll mod=1e9+7;
int t;
ll n,k,ans;
int main() {
	t=read();
	while(t--){
		n=read(),k=read();
		ans=n;
		if(k>=n) ans=1;
		else{
			for(ll i=min((ll)sqrt(n),k);i>=1;i--){
				if(n%i==0){
					if(n/i<=k) ans=min(ans,min(i,n/i));
					else ans=min(ans,n/i);
				}
			}
		}
		cout<<ans<<endl;
	}	
}');
INSERT INTO "news" VALUES (3, '3《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2020-09-31 22:23:00', 1111111);
INSERT INTO "news" VALUES (4, '4《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2020-10-31 22:23:00', 1111111);
INSERT INTO "news" VALUES (5, '5《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2020-11-31 22:23:00', 1111111);
INSERT INTO "news" VALUES (6, '6《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2020-12-31 22:23:00', 1111111);
INSERT INTO "news" VALUES (7, '7《忍者蛙》发售日公布 已上架Steam、支持简中', '3DMGAME', 'https://p9.pstatp.com/thumb/6eed00026c4eac713a44', '2021-01-31 22:23:00', 1111111);

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "sqlite_sequence";
CREATE TABLE "sqlite_sequence" (
  "name" ,
  "seq" 
);

-- ----------------------------
-- Records of sqlite_sequence
-- ----------------------------
INSERT INTO "sqlite_sequence" VALUES ('user', 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  "user_id" integer PRIMARY KEY AUTOINCREMENT,
  "username" text,
  "password" text
);

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "user" VALUES (1, 'root', 123456);
INSERT INTO "user" VALUES (2, 123, 123);

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS "video";
CREATE TABLE "video" (
  "vid" INTEGER NOT NULL,
  "vtitle" TEXT,
  "author" TEXT,
  "coverurl" TEXT,
  "headurl" TEXT,
  "playerurl" TEXT,
  "create_time" text,
  "update_time" text,
  "category_id" INTEGER,
  "like_num" INTEGER,
  "collect_num" INTEGER,
  PRIMARY KEY ("vid")
);

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO "video" VALUES (1, '1青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 1, 67, 87);
INSERT INTO "video" VALUES (2, '2青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2021-07-19 12:05:33', 1, 66, 88);
INSERT INTO "video" VALUES (3, '3青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 1, 66, 88);
INSERT INTO "video" VALUES (4, '4青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 1, 66, 88);
INSERT INTO "video" VALUES (5, '5青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 1, 66, 88);
INSERT INTO "video" VALUES (6, '6青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 1, 66, 88);
INSERT INTO "video" VALUES (7, '7青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 1, 66, 88);
INSERT INTO "video" VALUES (8, '8青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);
INSERT INTO "video" VALUES (9, '9青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);
INSERT INTO "video" VALUES (10, '10青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);
INSERT INTO "video" VALUES (11, '11青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);
INSERT INTO "video" VALUES (12, '12青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);
INSERT INTO "video" VALUES (13, '13青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);
INSERT INTO "video" VALUES (14, '14青龙战甲搭配机动兵，P城上空肆意1V4', '狙击手麦克', 'http://sf3-xgcdn-tos.pstatp.com/img/tos-cn-i-0004/527d013205a74eb0a77202d7a9d5b511~tplv-crop-center:1041:582.jpg', 'https://sf1-ttcdn-tos.pstatp.com/img/pgc-image/c783a73368fa4666b7842a635c63a8bf~360x360.image', 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4', '2020-07-14 11:21:45', '2020-07-19 12:05:33', 2, 66, 88);

-- ----------------------------
-- Table structure for video_social
-- ----------------------------
DROP TABLE IF EXISTS "video_social";
CREATE TABLE "video_social" (
  "id" INTEGER NOT NULL,
  "user_id" INTEGER,
  "video_id" INTEGER,
  "flag_like" integer,
  "flag_collect" integer,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of video_social
-- ----------------------------
INSERT INTO "video_social" VALUES (1, 1, 1, 1, 1);
INSERT INTO "video_social" VALUES (2, 1, 2, 0, 1);
INSERT INTO "video_social" VALUES (3, 1, 3, 1, 1);
INSERT INTO "video_social" VALUES (4, 1, 4, 1, 0);
INSERT INTO "video_social" VALUES (5, 1, 5, 0, 1);
INSERT INTO "video_social" VALUES (6, 1, 6, 0, 0);
INSERT INTO "video_social" VALUES (7, 1, 7, 1, 1);
INSERT INTO "video_social" VALUES (8, 1, 8, 1, 0);
INSERT INTO "video_social" VALUES (9, 1, 9, 0, 1);
INSERT INTO "video_social" VALUES (10, 1, 10, 0, 0);
INSERT INTO "video_social" VALUES (11, 1, 11, 1, 1);
INSERT INTO "video_social" VALUES (12, 1, 12, 1, 0);
INSERT INTO "video_social" VALUES (13, 1, 13, 0, 1);
INSERT INTO "video_social" VALUES (14, 1, 14, 0, 0);

-- ----------------------------
-- Auto increment value for user
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 2 WHERE name = 'user';

PRAGMA foreign_keys = true;
