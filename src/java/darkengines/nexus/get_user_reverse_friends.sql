SELECT u.id FROM user AS u
LEFT JOIN friendship AS f ON f.owner = u.id
INNER JOIN friendship AS fb ON fb.target = u.id AND fb.owner = f.target
WHERE f.target = ?