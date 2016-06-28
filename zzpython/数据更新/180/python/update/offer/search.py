#!/usr/bin/env python

import time
from StringIO import StringIO
from PyLucene import *


class TestFormatter(Formatter):
    
    
    def __init__(self):
        pass

    def highlightTerm(self, originalText, group):
        if group.getTotalScore() <= 0:
            return originalText    
        return "<font color="red">" + originalText + "</font>"

class Search:
    
    
    def __init__(self):
        STORE_DIR = "index"
        self.directory = FSDirectory.getDirectory(STORE_DIR, False)
        self.analyzer = ChineseAnalyzer()
        self.maxNumFragmentsRequired = 2
        self.fragmentSeparator = u"..."
        
    def search(self, query, start):
        searcher = IndexSearcher(self.directory)  
        query = query.decode('gbk')
        query = QueryParser.parse(query, "contents", self.analyzer)
        starttime = time.time()
        hits = searcher.search(query)        
        formatter = TestFormatter()
        highlighter = Highlighter(formatter, QueryScorer(query))
        highlighter.setTextFragmenter(SimpleFragmenter(60))
        resultdic = {}
        totalnum = hits.length()
        for i in range(10):
            index = start + i
            if index >= totalnum:
                break
            try:
                doc = hits.doc(index)
            except:
                continue
            text = doc.get("contents")
            tokenStream = self.analyzer.tokenStream("contents", StringIO(text)) 
            result = highlighter.getBestFragments(
              tokenStream,
              text,
              self.maxNumFragmentsRequired,
              self.fragmentSeparator)
            score = hits.score(index)
            if resultdic.has_key(score):
                score += 0.0001
            resultdic[score] = [result, doc.get("path")]
        stoptime = time.time()
        usetime = stoptime - starttime            
        searcher.close()    
        ks = resultdic.keys()
                
        return resultdic, totalnum, usetime
        

if __name__ == '__main__':
    tt = Search()
    command = raw_input("Query:").decode('gbk')
    tt.search(command, 0)