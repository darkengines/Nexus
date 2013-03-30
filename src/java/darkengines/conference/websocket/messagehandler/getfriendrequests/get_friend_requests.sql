SELECT u.id, u.email, u.display_name FROM friendship as f
LEFT OUTER JOIN friendship as fr ON fr.owner = f.target AND fr.target = f.owner
LEFT JOIN user as u ON u.id = f.owner
WHERE f.target = ? AND fr.id IS NULL;