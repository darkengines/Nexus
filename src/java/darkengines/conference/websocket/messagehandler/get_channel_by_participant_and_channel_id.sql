SELECT c.id, c.`name` FROM channel AS c
INNER JOIN channel_participant AS cp ON cp.user_id = ? AND cp.channel_id = c.id
WHERE c.id = ?;