SELECT u.`id`, u.`email`, u.`display_name` FROM `friendship` AS f 
LEFT JOIN `user` AS u ON f.target = u.id
INNER JOIN friendship AS fb ON fb.owner = u.id
WHERE f.owner = ?;