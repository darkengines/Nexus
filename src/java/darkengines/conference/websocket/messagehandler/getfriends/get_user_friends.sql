SELECT u.id, u.email, u.display_name FROM friendship AS f
JOIN friendship AS fb ON f.owner = fb.target AND f.target = fb.owner
JOIN `user` AS u ON u.id = f.target
WHERE f.owner = ?;