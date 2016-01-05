#-------------------------------------------------------------------------------
# Name:        mailSend.py
# Purpose:
#
# Author:      hadden_xiao
#
# Created:     15/07/2011
# Copyright:   (c) hadden_xiao 2011
# Licence:     <your licence>
#-------------------------------------------------------------------------------
#!/usr/bin/env python
# coding:utf-8

import sys, os, re
import time
import smtplib, email.mime.text

def sendMsgMail(strFrom, strTo, strCC, strSubject, strBody):
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

def sendFileMail(strFrom, strTo, strCC, strSubject, strFile):
    with open(strFile) as f:
        content = f.read()
        sendMsgMail(strFrom, strTo, strCC, strSubject, content)


def main():
    strFrom = "Hadden Xiao"
    strTo =  "hadden_xiao@trendmicro.com.cn"
    strCC = "dabaosod011@gmail.com"
    strSubject = "this is a test message, please ignore!"
    strBody = '''\
Dear all,

This is a test message for function test, please ignore!

Best regards,
Hadden Xiao
'''
    sendMsgMail(strFrom, strTo, strCC, strSubject, strBody)

if __name__ == '__main__':
    main()
