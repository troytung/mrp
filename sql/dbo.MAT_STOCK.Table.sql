USE [MRP]
GO
/****** Object:  Table [dbo].[MAT_STOCK]    Script Date: 2017/1/1 �U�� 11:16:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MAT_STOCK](
	[MAT_CODE] [varchar](20) NOT NULL,
	[AMOUNTS] [decimal](18, 6) NOT NULL,
	[CREATE_BY] [varchar](20) NOT NULL,
	[CREATE_DT] [datetime] NOT NULL,
	[MODIFY_BY] [varchar](20) NULL,
	[MODIFY_DT] [datetime] NULL,
 CONSTRAINT [PK_MAT_STOCK] PRIMARY KEY CLUSTERED 
(
	[MAT_CODE] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'�y�z', @value=N'��Ʈw�s��' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MAT_STOCK'
GO
