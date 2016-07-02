export FILEPATH=file:///macs.csv

// Complaints, companies, responses.

	// Uniqueness constraints.
CREATE CONSTRAINT ON (m:MacsBundle) ASSERT m.id IS UNIQUE;
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

MERGE (bundle:MacsBundle {id: (line.macsID)})

CREATE (bundle)-[:Contains]->(subject);



USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.LCSHtextAND IS NOT NULL AND
      line.LCSHidAND IS NOT NULL

MERGE (subject:Subject { id: (line.`LCSHidAND`) })
SET subject.text = (line.`LCSHtextAND`)

MERGE (bundle:MacsBundle {id: (line.macsID)})

CREATE (bundle)-[:Contains {operator: 'AND'}]->(subject);




USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.RameauText IS NOT NULL AND
      line.RameauId IS NOT NULL

MERGE (subject:Subject { id: (line.`RameauId`) })
SET subject.text = (line.`RameauText`)

MERGE (bundle:MacsBundle {id: (line.macsID)})

CREATE (bundle)-[:Contains ]->(subject);



USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.RameauTextAnd IS NOT NULL AND
      line.RameauIdAnd IS NOT NULL

MERGE (subject:Subject { id: (line.`RameauIdAnd`) })
SET subject.text = (line.`RameauTextAnd`)

MERGE (bundle:MacsBundle {id: (line.macsID)})

CREATE (bundle)-[:Contains {operator: 'AND'}]->(subject);





USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.GNDText IS NOT NULL AND
      line.GNDid IS NOT NULL

MERGE (subject:Subject { id: (line.`GNDid`) })
SET subject.text = (line.`GNDText`)

MERGE (bundle:MacsBundle {id: (line.macsID)})

CREATE (bundle)-[:Contains ]->(subject);



USING PERIODIC COMMIT
LOAD CSV WITH HEADERS
FROM {FILEPATH} AS line
WITH line
WHERE line.GNDTextAnd IS NOT NULL AND
      line.GNDidAnd IS NOT NULL

MERGE (subject:Subject { id: (line.`GNDidAnd`) })
SET subject.text = (line.`GNDTextAnd`)

MERGE (bundle:MacsBundle {id: (line.macsID)})

CREATE (bundle)-[:Contains {operator: 'AND'}]->(subject);




