SELECT COUNT(user_id) > 0 AS is_participant FROM channel_participant WHERE channel_id=? AND user_id=?;