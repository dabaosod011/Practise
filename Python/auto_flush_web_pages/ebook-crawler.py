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

def get_bookinfo(content):
    soup = BeautifulSoup.BeautifulSoup(content, fromEncoding="gbk")
    booktitle = soup.findAll('title')[0].text
    print booktitle

    bkcontent = booktitle + ":\n\n"
    chaptercontent = soup.findAll('div', {'class': 'bk-article-body','id':'bk-article-body'})[0].findChildren('p')
    for eachitem in chaptercontent:
        cc = unicode(eachitem)
        start = cc.find('>')
        end = cc.rfind('<')
        cc = cc[start+1:end].strip()+"\n\n"
        #print cc
        bkcontent += cc
    return  bkcontent

def main():
    savedbook = open("book.txt",'a')
    for idx in range(0,24):
        curl = r"http://data.book.163.com/book/section/000BINPZ/000BINPZ"+str(idx)+r".html"
        webpage = getPagecontent(curl)
        bkcontent = get_bookinfo(webpage)
        #print bkcontent
        savedbook.write(bkcontent.encode("gbk"))
        savedbook.write("\n===============\n")



if __name__ == '__main__':
    main()

