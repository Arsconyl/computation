Input : CSV file

> OPERATOR;NUMBER1;NUMBER2;NUMBER3

OPERATOR:

- MOD => %
- ADD => +
- DIV => /
- SUB => -
- MULTI => *
- CUSTOM => [ROW:COLUMN]OPERATOR[ROW:COLUMN]=[ROW:COLUMN]

  [:2] => current row, column 2

  [2:] => take the result of the row 2

  [3:2] => take the column 2 of the row 3

Example custom rule:
[2:3]+[3:6]=[:3] => Take value in row 2 column 3 add value from row 3 column 6 store in current row in column 3

In the output file, the 3 last row is dedicated to the sum of each column


> RULE;RESULT1;RESULT2;RESULT3
> ADD;10;0;0
> CUSTOM;0;12;0
> CUSTOM;0;0;8
> TOTAL1;10;12;8
> TOTAL2;10;20;0
> TOTAL3;30;0;0

Keep in mind, future rule can be implemented.
CUSTOM rule can be multiple in one row, all these rules is separated by coma ','.
You can use '|' or ';' for the separator in the CSV file.

OUTPUT CSV file:

> RULE;RESULT1;RESULT2;RESULT3

RULE => contain the name of the rule (ADD, CUSTOM, SUB etc..)
RESULT1;RESULT2;RESULT3 => different results from operation.

JAVA SPRING BOOT PROJECT

Endpoint:
	POST /api/computation
		Upload csv file and return a csv file that contain the result of the computation data
	GET /api/rules
		Get all the rules are available in the api
	POST /api/async/computation
		Upload input file et process the data in async. Return a uuid of the process
	GET /api/async/computation/:uuid
		Get Output file corresponding at the uuid given.
	GET /api/async/status/:uuid
		Get Status of the target computation

Error case:
	unknown rule => 404 rule unknown [line of the row are in error] 
	can't parse input file => 500 can't parse file
	
