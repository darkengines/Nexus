SELECT f2.id AS id, u.id AS user_id FROM friendship AS f1
RIGHT OUTER JOIN friendship AS f2 ON f1.owner = f2.target AND f1.target = f2.owner
JOIN `user` AS u ON u.id = f2.owner
WHERE f1.id IS NULL AND f2.target = ?;