USE [MRP]
GO
/****** Object:  User [mrpuser]    Script Date: 2017/1/1 ¤U¤È 11:16:37 ******/
CREATE USER [mrpuser] FOR LOGIN [mrpuser] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [mrpuser]
GO
