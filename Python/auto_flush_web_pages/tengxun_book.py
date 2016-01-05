#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      hadden_xiao
#
# Created:     25/04/2012
# Copyright:   (c) hadden_xiao 2012
# Licence:     <your licence>
#-------------------------------------------------------------------------------
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
    booktitle = soup.findAll('h1')[0].text
    print booktitle

    bkcontent = booktitle + ":\n\n"
    chaptercontent = soup.findAll('div', {'id':'content'})[0].text
    bkcontent = bkcontent + chaptercontent

    return  bkcontent

def main():
    savedbook = open("book.txt",'a')
    for idx in range(1,27):
        curl = r"http://book.qq.com/s/book/0/24/24872/"+str(idx)+r".shtml"
        webpage = getPagecontent(curl)
        bkcontent = get_bookinfo(webpage)
        bkcontent = bkcontent.replace(" ","\n")
        savedbook.write(bkcontent.encode("gbk"))
        savedbook.write("\n\n")

if __name__ == '__main__':
    main()