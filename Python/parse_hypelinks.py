import os,sys,re
import BeautifulSoup


def main():

    with open("example.html") as f:
        content = f.read()
        soup = BeautifulSoup.BeautifulSoup(content)
        for item in soup.findAll('a', href=True):
            link = item["href"]
            if link.endswith(".pdf"):
                cmd =  "wget  --no-check-certificate \"" + link+ "\""
                print cmd

if __name__ == '__main__':
    main()
