SELECT u.id, fb.id IS NOT NULL AS reverse_friendship FROM user AS u
LEFT JOIN friendship AS f ON f.owner = u.id
LEFT JOIN friendship AS fb ON fb.target = u.id AND fb.owner = f.target
WHERE f.target = ?