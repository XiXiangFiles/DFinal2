#-*- coding: utf-8 -*-
import pymysql
import requests as resq
from urllib2 import urlopen 
from bs4 import BeautifulSoup
import json

# http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/1458.png
# 所有的卡跟所有的銀行
# http://www.cardori.com.tw/rest/cards/IDWithBankID  
user="pi"
pwd="nccutest"


def I_Discount():
	sql="SELECT `C_Num` FROM `Card` WHERE 1"
	cur=DB('127.0.0.1',user,pwd,'DFinal')
	cur.execute(sql)
	for x in xrange(1,cur.rowcount):
		C_Num=cur.fetchone()
		url="http://www.cardori.com.tw/rest/cards/competition?cardID=%s" % (C_Num[0])
		print url
		res=resq.get(url)
		dtake=json.loads(res.text)
		items=json.dumps(dtake[0].get("items"), ensure_ascii=False)
		data=json.loads(items)

		eatingScore=json.dumps(data[0].get("eatingScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"eatingScore",eatingScore)
		movieScore=json.dumps(data[0].get("movieScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"movieScore",movieScore)
		annualFeeScore=json.dumps(data[0].get("annualFeeScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"annualFeeScore",annualFeeScore)
		insuranceScore=json.dumps(data[0].get("insuranceScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"insuranceScore",insuranceScore)
		publicScore=json.dumps(data[0].get("publicScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"publicScore",publicScore)
		shoppingScore=json.dumps(data[0].get("shoppingScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"shoppingScore",shoppingScore)
		cashBackScore=json.dumps(data[0].get("cashBackScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"cashBackScore",cashBackScore)
		firstSwipeScore=json.dumps(data[0].get("firstSwipeScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"firstSwipeScore",firstSwipeScore)
		bonusScore=json.dumps(data[0].get("bonusScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"bonusScore",bonusScore)
		smartpayScore=json.dumps(data[0].get("smartpayScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"smartpayScore",smartpayScore)
		mileageScore=json.dumps(data[0].get("mileageScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"mileageScore",mileageScore)
		otherScore=json.dumps(data[0].get("otherScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"otherScore",otherScore)
		parkingScore=json.dumps(data[0].get("parkingScore").get("content"), ensure_ascii=False)
		Save_Discount(C_Num[0],"parkingScore",parkingScore)

	cur.close()


def Save_Discount(C_Num,D_Type,Content):
	# string=Content.replace("\\"," ")
	try:
		string=Content.split("\\n")
		string=string[1:-1]
		for x in xrange(0,len(string)):
			string2=string[x].split("|")
			title=string2[0]
			content=string2[1]
			check_sql="SELECT * FROM `Discount` WHERE `C_Num`= '%s' and `D_Type`= '%s' and `D_Title`= '%s' and `D_Content` ='%s'" % (C_Num,D_Type,title[2:],content)
			sql="INSERT INTO `Discount`( `C_Num`, `D_Type`, `D_Title`, `D_Content`) VALUES ('%s','%s','%s','%s')" % (C_Num,D_Type,title[2:],content)
			SaveData(check_sql,sql)
	except Exception as e:
		print e

	


def F_BankandF_cardID():
	url="http://www.cardori.com.tw/rest/cards/IDWithBankID"
	html=urlopen(url)
	bsObj=BeautifulSoup(html)
	dtake=json.loads(bsObj.get_text())
	for x in xrange(0,len(dtake)):
		B_Name=json.dumps(dtake[x].get("bankName"), ensure_ascii=False)
		sql="INSERT INTO `Bank` (`B_Num`, `B_Name`, `B_Info`) VALUES (%s, %s, '')" % ("NULL",B_Name)
		check_sql="SELECT * FROM  `Bank` WHERE `B_Name` = %s" % (B_Name)
		SaveData(check_sql,sql)
		C_Num=json.dumps(dtake[x].get("ID"), ensure_ascii=False)
		C_Name=json.dumps(dtake[x].get("cardName"), ensure_ascii=False)

		check_sql_Card="SELECT * FROM `Card` WHERE `C_Num`= %s" % (C_Num)
		sql_Card="INSERT INTO `Card`(`C_Num`, `B_Num`, `C_Name`, `C_Info`) VALUES (%s,(SELECT `B_Num` FROM `Bank` WHERE `B_Name`=%s) ,%s, %s)" % (C_Num,B_Name,C_Name,"'NULL'")
		# print check_sql_Card,"\n",sql_Card
		SaveData(check_sql_Card,sql_Card)
	return


def DB(host,user,password,db):
	conn = pymysql.connect(host,user,password,db, charset='utf8')
	cur=conn.cursor()
	return cur


def SaveData(check_sql,sql):
	# print check(Database,table,matadata,data)
	if check(check_sql) ==True:
	    conn = pymysql.connect(host='127.0.0.1',user=user,password=pwd,db='DFinal',charset='utf8')
	    cur=conn.cursor()
	    try:
		    cur.execute(sql)
		    conn.commit()
		    cur.close()
		    print sql
	    except Exception as e:
		    print "error=\t %s"% str(e)
        
	return


def check(sql):
	cur=DB('127.0.0.1',user,pwd,'DFinal')
	# sql="SELECT * FROM  %s WHERE %s = %s" % (table, matadata,data)
	try:
		cur.execute(sql)
	except Exception as e:
		print "error=\t %s"% str(e)
	
	if cur.rowcount==1:
		return False
	cur.close()
	return True


def main():
	
	F_BankandF_cardID()
	I_Discount()
	# print "\""


main()
