#!/usr/bin/env python

import sys, os, re
import urllib2
import BeautifulSoup
import smtplib
import email.mime.text

def getPagecontent(url):
    print "downloading %s" %(url)
    #header = {'User-Agent': 'Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)'}
    try:
    	#request = urllib2.Request(url, headers = header)
    	urllib2.urlopen(url, 'pageno=1')
    	content = sock.read()
    	return content
    except:
	    print 'download fail:%s' % (url)
	    return ""
	   
def get_storeinfo(content):
    soup = BeautifulSoup.BeautifulSoup(content, fromEncoding="utf8")
    saleInfos = soup.findAll('li', {'class': 'shipping-description'})
    #print saleInfos
    saleInfo = saleInfos[0].findChildren('span')[0].text
    return ('storeinfo', unicode(saleInfo))	
    
def main():
	noticeurl = r"http://iecms.ec.com.cn/iecms/index.jsp"
	
    	strBody = '''\
Dears all,

ipad 2 is available now. please access : %s 

Thanks!
		''' %noticeurl
	
	
	content = getPagecontent(noticeurl)
	open("web_content.txt",'wt').write(content)
	#print get_storeinfo(content)
	#if get_storeinfo(content)[1] != u'\u6682\u65e0\u4f9b\u5e94':
	#	funcSendmail("dabaosod","hadden_xiao@trendmicro.com.cn","ipad is available now",strBody)
    	
if __name__ == '__main__':
    main()   
	   
    