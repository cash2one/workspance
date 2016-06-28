#!/usr/bin/env python

import os
import PyLucene


class IndexFiles:
    """
    create index by PyLucene, just need your dir path,
    the result files saved in the directory index in
    current path
    """

    def __init__(self, root, storeDir, analyzer):
        if not os.path.exists(storeDir):
            os.mkdir(storeDir)
        analyzer = PyLucene.StandardAnalyzer()       
        store = PyLucene.FSDirectory.getDirectory(storeDir , True)
        writer = PyLucene.IndexWriter(store, analyzer, True)
        self.indexDocs(root, writer)
        print 'optimizing index',
        writer.optimize()
        writer.close()
        print 'done'

    def indexDocs(self, root, writer):
        for root, dirnames, filenames in os.walk(root):
            for filename in filenames:
                if not filename.endswith('.txt'):
                    continue
                print "adding", filename
                try:
                    path = os.path.join(root, filename)
                    file = open(path)
                    contents = unicode(file.read(), 'gbk')
                    file.close()
                    doc = PyLucene.Document()
                    doc.add(PyLucene.Field.Keyword(u"name", filename.decode('gbk')))
                    doc.add(PyLucene.Field.Text(u"path", path.decode('gbk')))
                    if len(contents) > 0:
                        pass
                        doc.add(PyLucene.Field.Text(u"contents", contents))
                    else:
                        print "warning: no content in %s" % filename
                    writer.addDocument(doc)
                except Exception, e:
                    print "Failed in indexDocs:", e

def indexmain(path):
    try:
        IndexFiles(path, "index", PyLucene.StandardAnalyzer())
        return ''
    except Exception, e:
        return e
    
if __name__ == '__main__':
    indexpath = raw_input("path: ")
    indexmain(indexpath)