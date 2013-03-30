SELECT u.id, u.email, u.display_name FROM friendship AS f 
LEFT OUTER JOIN friendship AS fb ON fb.target = f.owner AND fb.owner = f.target
LEFT JOIN `user` AS u ON f.target = u.id
WHERE f.owner = ? AND fb.id IS NULL;