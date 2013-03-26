SET @a = ?, @b=?;
SELECT COUNT(id) > 1 AS are_friends FROM friendship where (target = @a && owner = @b) || (target=@b && owner=@a);