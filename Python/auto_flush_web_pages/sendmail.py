#!/usr/bin/env python
# coding:utf-8

import sys, os, re
import time
import smtplib, email.mime.text

def funcSendmail(strFrom, strTo, strCC, strSubject, strBody):
    print 'Begin sendmail'
    msg = email.mime.text.MIMEText(strBody)
    msg['Subject'] = strSubject
    msg['From'] = strFrom
    msg['To']   = strTo
    if strCC:
        msg['CC']   = strCC
    msg['X-Priority'] =  '1'
    msg['X-MSMail-Priority'] =  'High'
    try:
        smtp = smtplib.SMTP()
        smtp.connect('CDCEXMAIL02.tw.trendnet.org', 25)
        smtp.sendmail(strFrom, strTo, msg.as_string())
        smtp.close()
    except Exception, e:
        print 'sendmail error: %r' % str(e)
    print 'End sendmail'

def funcSendmailToTeam():
    strLocalVersion = open('version.txt', 'rb').read().strip()
    strFrom = 'AutoDownload@trendmicro.com.cn'
    strTo = 'AllofTrendTI-HEQA@dl.trendmicro.com;AllofTrendTI-HEIntern@dl.trendmicro.com'
    strCC = 'AllofTrendTI-HERD@dl.trendmicro.com'
    #strTo = 'phus_lu@trendmicro.com.cn'
    #strCC = 'phus_lu@trendmicro.com.cn'
    strSubject = 'Ti-HE 5.0 *Repack* Build %s is Downloaded' % strLocalVersion
    strBody = '''\
Dears all,

This Build is Ti-HE 5.0 *Repack* Build %(version)s.
You can get the build from the following paths:

\\\\10.204.16.2\\Project\\PCC\\AutoDownload\\TiHE5.0Repack\\

Thanks!
''' % dict(version=strLocalVersion)
    funcSendmail(strFrom, strTo, strCC, strSubject, strBody)

if __name__ == '__main__':
    funcSendmailToTeam()

