SELECT c.id, c.`name` FROM channel
INNER JOIN channel_participant AS cp ON cp.user_id = ? AND cp.channel_id = c.id
WHERE c.id = ?;