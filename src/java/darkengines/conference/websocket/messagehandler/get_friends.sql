SELECT f1.target FROM friendship AS f1, friendship AS f2
WHERE f1.owner = ? AND f1.target = f2.owner;