# COEN 352 – Bixi 2025 Data Analysis

**Authors:** Jules Wygas · Clement Lepage

A data structures and algorithms project querying **14 million Bixi trip records** from 2025. All queries run against pre-organized hash tables built at load time, keeping most operations at O(1) or O(N) in the size of results rather than total data.

> Note: Requirements 4 and 6 are not implemented.

## Data Model

Each CSV line becomes a `BixiTrip` object stored once in memory, with references distributed across multiple purpose-built simple tables — indexed by station, date, duration, and arrondissement. Only useful fields are kept: coordinates are discarded, timestamps are shortened to seconds, and station/arrondissement names are mapped to integers via a custom dictionary. Day-of-year and hour-of-day are pre-computed at parse time.

**Load performance:**

| Rows | Time |
|---|---|
| 150 | ~0s |
| 670,000 | 2s |
| 14,000,000 | 40s |

Scaling factor of 20.9× on rows → 20× on time, confirming O(N) load.

## Implemented Requirements

| # | Query | Complexity | Author |
|---|---|---|---|
| 0 | Load & index all data | O(N) | Both |
| 1 | Trips by station (start/end/both) | O(N) | Clement |
| 2 | Trips by month | O(N) | Clement |
| 3 | Trips above minimum duration | O(D + T) | Jules |
| 5 | Top K arrondissements | **O(1)** | Clement |
| 7 | Rush hour for a given month | O(D) | Jules |
| 8 | Compare two months (top K stations) | O(N) | Clement |

## Highlight: O(1) Top-K Arrondissements

The arrondissement input file is pre-sorted by trip count, so returning the top K is a direct array slice — no sorting required at query time.

## Full Report

📄 [Programming Assignment Analysis](./PROGRAMMING_ASSIGNMENT_ANALYSIS_docx.pdf)
