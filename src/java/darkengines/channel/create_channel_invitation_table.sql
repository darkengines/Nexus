CREATE  TABLE `channel_invitation` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `channel_id` BIGINT NOT NULL ,
  `user_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );