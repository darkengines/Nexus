SELECT c.id, c.name FROM channel_participant AS cp
JOIN channel AS c ON c.id = cp.channel_id
WHERE cp.user_id = ?
UNION
SELECT c.id, c.name FROM channel_invitation AS ci
JOIN channel AS c ON c.id = ci.channel_id
WHERE ci.user_id = ?;