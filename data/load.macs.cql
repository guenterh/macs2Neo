export FILEPATH=file:///macs.csv

// Complaints, companies, responses.

	// Uniqueness constraints.
CREATE CONSTRAINT ON (m:MacsBundel) ASSERT m.id IS UNIQUE;
CREATE CONSTRAINT ON (s:Subject) ASSERT s.id IS UNIQUE;

// Load.

USING PERIODIC COMMIT
LOAD CSV WITH HEADERS 
FROM {FILEPATH} AS line
WITH line
WHERE line.LCSHtext IS NOT NULL AND
      line.LCSHid IS NOT NULL

MERGE (subject:Subject { id: (line.`LCSHid`) })
SET subject.text = (line.`LCSHtext`)

MERGE (bundle:MacsBundel {id: (line.macsID)})

CREATE (bundle)-[:Contains]->(subject);



USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.LCSHtextAND IS NOT NULL AND
      line.LCSHidAND IS NOT NULL

MERGE (subject:Subject { id: (line.`LCSHidAND`) })
SET subject.text = (line.`LCSHtextAND`)

MERGE (bundle:MacsBundel {id: (line.macsID)})

CREATE (bundle)-[:Contains {operator: 'AND'}]->(subject);




USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.RameauText IS NOT NULL AND
      line.RameauId IS NOT NULL

MERGE (subject:Subject { id: (line.`RameauId`) })
SET lcshAnd.text = (line.`LCSHtextAND`)

MERGE (bundleLcsh:MacsBundel {id: (line.macsID)})

CREATE (bundleLcsh)-[:Contains {operator: 'AND'}]->(lcshAnd);

