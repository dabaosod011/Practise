#!/usr/bin/env python
# coding:utf-8

import sys, os, re, time
import logging
import socket, httplib, urllib2, urlparse
import urlparse
import BeautifulSoup

logging.basicConfig(level=logging.INFO, format='%(levelname)s - - %(asctime)s %(message)s', datefmt='[%d/%b/%Y %H:%M:%S]')
book_GanShiJiuMei = r"http://www.my285.com/wuxIa/xy/ymlh/"

def generateUrls(domain, chapNumbers):
    urls = []
    for i in range(1,chapNumbers):
        if i<10:
            urls.append(domain+"00"+str(i)+".htm")
        elif i<100 and i>=10:
            urls.append(domain+"0"+str(i)+".htm")
        else:
            urls.append(domain+str(i)+".htm")
    return urls

def getPagecontent(url):
    print "downloading %s" %(url)
    header = {'User-Agent': 'Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))'}
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
    booktitle = soup.findAll('td', {'colspan':'2'})[0].text
    print booktitle

    bkcontent = booktitle + "\n\n"
    chaptercontent = soup.findAll('td', {'colspan':'2'})[2].text
    bkcontent = bkcontent + chaptercontent

    return  bkcontent

def main():
    urls = generateUrls(book_GanShiJiuMei, 199)
    savedbook = open("book.txt",'a')
    for eachChapter in urls:
        webpage = getPagecontent(eachChapter)
        if webpage != "":
            bkcontent = get_bookinfo(webpage)
            savedbook.write(bkcontent.encode("gbk"))
            savedbook.write("\n\n")
            savedbook.flush()
    savedbook.close()

if __name__ == '__main__':
    main()
