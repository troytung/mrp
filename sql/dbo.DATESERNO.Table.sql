USE [MRP]
GO
/****** Object:  Table [dbo].[DATESERNO]    Script Date: 2017/1/1 �U�� 11:16:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DATESERNO](
	[DATESER_COLNAME] [varchar](50) NOT NULL,
	[SERDATE] [datetime2](0) NOT NULL,
	[SERNO] [int] NULL,
	[CREATE_BY] [varchar](20) NOT NULL,
	[CREATE_DT] [datetime] NOT NULL,
	[MODIFY_BY] [varchar](20) NULL,
	[MODIFY_DT] [datetime] NULL,
 CONSTRAINT [PK_DATESERNO] PRIMARY KEY CLUSTERED 
(
	[DATESER_COLNAME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
