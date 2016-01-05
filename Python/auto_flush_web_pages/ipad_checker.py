#!/usr/bin/env python

import sys, os, re
import urllib2
import BeautifulSoup
import smtplib
import email.mime.text

def getPagecontent(url):
    print "downloading %s" %(url)
    header = {'User-Agent': 'Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)'}
    try:
    	request = urllib2.Request(url, headers = header)
    	sock = urllib2.urlopen(request)
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

def funcSendmail(strFrom, strTo, strSubject, strBody):
    print 'Begin sendmail'
    msg = email.mime.text.MIMEText(strBody)
    msg['Subject'] = strSubject
    msg['From'] = strFrom
    msg['To']   = strTo
    #msg['X-Priority'] =  '1'
    #msg['X-MSMail-Priority'] =  'High'
    try:
        smtp = smtplib.SMTP()
        smtp.connect('CDCEXMAIL02.tw.trendnet.org', 25)
        smtp.sendmail(strFrom, strTo, msg.as_string())
        smtp.close()
    except Exception, e:
        print 'sendmail error: %r' % str(e)
    print 'End sendmail'

def main():
	noticeurl = r"http://store.apple.com/cn/browse/home/shop_ipad/family/ipad/select?mco=MjE2MjYyNzA"

    	strBody = '''\
Dears all,

ipad 2 is available now. please access : %s

Thanks!
		''' %noticeurl


	#content = getPagecontent(noticeurl)
	#open("web_content.txt",'wt').write(content)
	#print get_storeinfo(content)
	#if get_storeinfo(content)[1] != u'\u6682\u65e0\u4f9b\u5e94':
	funcSendmail("dabaosod","hadden_xiao@trendmicro.com.cn","ipad is available now",strBody)

if __name__ == '__main__':
    main()

