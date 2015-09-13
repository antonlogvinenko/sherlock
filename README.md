# Sherlock
Automatic build failure investigation tool

From commits
- [DONE] extract commits from repository
- [DONE] list of chunks
- [DONE] list of typed chunks with significant lines
- [DONE] list of typed chunks with significant words
- [DONE] set of insignificant words (keywords)
- [DONE] enriched vocabulary - enriched and joined set for commit
- get commits in range
- for each commit in range get author and submit fetched InputStream to another pool
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
- binary files in diff have another format