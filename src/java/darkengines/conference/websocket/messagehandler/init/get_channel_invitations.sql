SELECT c.id, c.name FROM channel_invitation AS CI
JOIN channel AS c ON c.id = ci.channel_id
WHERE user_id = ?