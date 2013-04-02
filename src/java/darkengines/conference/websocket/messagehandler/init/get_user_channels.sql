SELECT c.id, c.name FROM channel_participant AS cp
JOIN channel AS c ON c.id = cp.channel_id
WHERE cp.user_id = ?;