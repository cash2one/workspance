from sphinxapi import *
cl = SphinxClient()
port = 9317
cl.SetServer ( "search.zz91server.com", port )
#res = cl.UpdateAttributes ( 'news', [ 'deleted' ], {107126:[1]} )
#print res
cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
cl.SetFilter('deleted',[1])
cl.SetLimits (0,100,100)
res = cl.Query ('',"news")
if res:
    if res.has_key('matches'):
        tagslist=res['matches']
        for match in tagslist:
            id=match['id']
            print id