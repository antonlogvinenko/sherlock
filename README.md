# Sherlock
Automatic build failure investigation tool

From commits
- [DONE] extract commits from repository
- [DONE] list of chunks
- [DONE] list of typed chunks with significant lines
- [DONE] list of typed chunks with significant words
- [DONE] set of insignificant words (keywords)
- [DONE] enriched vocabulary - enriched and joined set for commit
- [DONE] get commits in range
- [DONE] for each commit in range get author and submit fetched InputStream to another pool
- [DONE] get map author .-> set[word]

Prototype:
- [DONE] build vocabulary for error log
- [DONE] make jar binary with maven, accept parameters from console
- [DONE] authentication support
- [DONE] intersect commits/error
- [DONE] sort final metric

From error log
- download log file
- learn to find lines with possible errors, extract several next lines

Improvements
- svn info finds commits like branching that aren't relevant - useless info but appear have high metric value
- find proper metric
- try to use this tool, make observations
- binary files in diff have another format