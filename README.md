# Sherlock
Automatic build failure investigation tool

From commits
- extract commits from repository
- [DONE] list of chunks
- list of typed chunks with significant lines
- list of typed chunks with significant words
- [DONE] set of insignificant words (keywords)
- [DONE] enriched vocabulary - enriched and joined set for commit
- get map <author, set<word>>

From error log
- download log file
- learn to find lines with possible errors, extract several next lines
- build vocabulary for error log

Core
- intersect commits/error
- find metric to sort most dangerous commits

Improvements (not critical)
- set of insignificant words per language