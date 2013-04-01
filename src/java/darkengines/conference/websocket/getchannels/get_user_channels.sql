SELECT c.id, c.name FROM channel_participant AS cp
INNER JOIN channel AS c ON c.id = cp.channel_id
WHERE user_id = ?;