SELECT u.id, u.display_name FROM friendship AS f1, friendship AS f2
JOIN `user` AS u ON u.id = f2.owner
WHERE ? = f2.target AND f1.target = f2.owner
UNION
SELECT u.id, u.display_name FROM channel_participant AS cp1, channel_participant AS cp2
JOIN `user` AS u ON u.id = cp2.user_id
WHERE cp1.user_id = ? AND cp2.channel_id = cp1.channel_id AND cp2.user_id != cp1.user_id
UNION
SELECT u.id, u.display_name FROM friendship AS f1
RIGHT OUTER JOIN friendship AS f2 ON f1.owner = f2.target AND f1.target = f2.owner
JOIN `user` AS u ON u.id = f2.owner
WHERE f1.id IS NULL AND f2.target = ?
UNION
SELECT u.id, u.display_name FROM friendship AS f1
RIGHT OUTER JOIN friendship AS f2 ON f1.owner = f2.target AND f1.target = f2.owner
JOIN `user` AS u ON u.id = f2.target
WHERE f1.id IS NULL AND f2.owner = ?
UNION
SELECT u.id, u.display_name FROM channel_invitation AS ci, channel_participant AS cp
JOIN `user` AS u ON u.id = cp.user_id
WHERE ci.user_id = ? AND cp.channel_id = ci.channel_id;