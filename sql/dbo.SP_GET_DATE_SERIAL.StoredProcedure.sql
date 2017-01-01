USE [MRP]
GO
/****** Object:  StoredProcedure [dbo].[SP_GET_DATE_SERIAL]    Script Date: 2017/1/1 �U�� 11:16:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--====================================================================
--SP Name     : dbo.SP_GET_DATE_SERIAL 
--Function    : ���Ǹ�
--Author      : 
--Create Date : 
--Source Table: [1]dbo.DATESERNO
--              [2]dbo.DATESERNO_SETS
--Target Table: [1]dbo.DATESERNO
--Parameter   : [1]@I_DATESER_COLNAME
--Return Value: [1]@O_DATESER_CODE
--Exec SP Name: 
--Modify His  : 
--===================================================================

CREATE PROCEDURE [dbo].[SP_GET_DATE_SERIAL] (
					 @I_DATESER_COLNAME VARCHAR(120),
					 @O_DATESER_CODE    VARCHAR(20) OUTPUT)
AS BEGIN 

    SET NOCOUNT ON 
    SET ANSI_WARNINGS ON

    BEGIN TRY
        BEGIN TRANSACTION        
        DECLARE @V_ERROR_SEVERITY INT,               --RAISERROR�ܼ�:ERROR_SEVERITY()
                @V_ERROR_STATE    INT,               --RAISERROR�ܼ�:ERROR_STATE()
                @V_ERROR_MESSAGE  NVARCHAR(4000),    --RAISERROR�ܼ�:ERROR_MESSAGE()
                @V_TEXT           VARCHAR(500),
                @V_SERDATE        VARCHAR(8),
                @V_DATESERNO      INT,
                @SERNO_LEN        INT,
                @V_SERDATE_TYPE   VARCHAR(1)

        SET @V_TEXT    = ''

        --========== SP START ==========

        SET @V_TEXT = '[1]��DATESERNO�ثe�s��'        
        SELECT @V_DATESERNO = SERNO, @V_SERDATE = CONVERT(VARCHAR,SERDATE,112) FROM dbo.DATESERNO WITH(UPDLOCK) WHERE DATESER_COLNAME = @I_DATESER_COLNAME
		--SELECT @V_SERDATE_TYPE = SERDATE_TYPE FROM DATESERNO_SETS WITH(UPDLOCK) WHERE DATESER_COLNAME='EAI_WM.CTF_A_HOST_SEQ_NO'
        
        IF @V_SERDATE IS NOT NULL
        BEGIN
            IF @V_SERDATE <> CONVERT(VARCHAR,GETDATE(),112) --AND @V_SERDATE_TYPE <> 'S'
            BEGIN
                SET @V_TEXT = '[2]���s�w�q�y����'
                SET @V_DATESERNO = 1;
                UPDATE dbo.DATESERNO SET SERNO = @V_DATESERNO, SERDATE = GETDATE() WHERE DATESER_COLNAME = @I_DATESER_COLNAME
                SET @V_SERDATE = CONVERT(VARCHAR,GETDATE(),112)
            END
            ELSE BEGIN
                SET @V_TEXT = '[2]��s�ثe���y����: '+CAST(@V_DATESERNO+1 AS VARCHAR)
                SET @V_DATESERNO = @V_DATESERNO + 1;
                UPDATE dbo.DATESERNO SET SERNO = @V_DATESERNO WHERE DATESER_COLNAME = @I_DATESER_COLNAME
            END           
        END
        ELSE BEGIN
           SET @V_TEXT = '[2]�bdbo.DATESERNO�䤣����,�h�N�Ǹ��]��NULL'
           SET @V_DATESERNO = NULL
           SET @V_SERDATE = CONVERT(VARCHAR,GETDATE(),112)
        END
        
        SET @V_TEXT = '[3]�y��������'
        SELECT @SERNO_LEN = COALESCE(SERNO_LEN,6) FROM dbo.DATESERNO_SETS WHERE DATESER_COLNAME = @I_DATESER_COLNAME
        SET @V_DATESERNO = POWER (10,@SERNO_LEN) + @V_DATESERNO
        
--        IF @V_SERDATE_TYPE = 'S'
--	        BEGIN
--	            SET @O_DATESER_CODE = COALESCE(SUBSTRING(CONVERT(VARCHAR,@V_DATESERNO),2,LEN(@V_DATESERNO)),'')
--	        END
--        ELSE BEGIN
	        SET @V_TEXT = '[3]�դ���榡' 
	        SELECT @O_DATESER_CODE = 
	            COALESCE(PREFIX_CODE,'') + 
	            CASE WHEN COALESCE(PREFIX_CODE,'') !='' THEN COALESCE(CONJ_SIGN,'') ELSE '' END + 
	            CASE WHEN SERDATE_TYPE ='A' THEN CONVERT (VARCHAR,@V_SERDATE,112) 
	            WHEN SERDATE_TYPE ='B' THEN SUBSTRING(CONVERT (VARCHAR,@V_SERDATE,112),3,8)
	            WHEN SERDATE_TYPE ='C' THEN 
	                CASE WHEN LEN(CONVERT(VARCHAR,SUBSTRING(CONVERT(VARCHAR,@V_SERDATE,112),1,4)-1911))=2 THEN '0' + CONVERT(VARCHAR,SUBSTRING(CONVERT(VARCHAR,@V_SERDATE,112),1,4)-1911) 
	                ELSE CONVERT(VARCHAR,SUBSTRING(CONVERT(VARCHAR,@V_SERDATE,112),1,4)-1911) END + SUBSTRING(CONVERT(VARCHAR,@V_SERDATE,112),5,2) + SUBSTRING(CONVERT(VARCHAR,@V_SERDATE,112),7,2)
	            WHEN SERDATE_TYPE ='D' THEN NULL
	            WHEN SERDATE_TYPE ='X' THEN ''
	            	ELSE CONVERT (VARCHAR,@V_SERDATE,112) END + COALESCE(CONJ_SIGN,'') + COALESCE(SUBSTRING(CONVERT(VARCHAR,@V_DATESERNO),2,LEN(@V_DATESERNO)),'')
	        FROM dbo.DATESERNO_SETS
	        WHERE DATESER_COLNAME = @I_DATESER_COLNAME  
--        END
        
        COMMIT TRANSACTION
        --========== SP  END  ========== 
              
    END TRY   
    BEGIN CATCH
        ROLLBACK TRANSACTION
        --����ERROR MESSAGE:50001�q��AP        
        SELECT @V_ERROR_SEVERITY = ERROR_SEVERITY(), @V_ERROR_STATE = ERROR_STATE(), @V_ERROR_MESSAGE = ERROR_MESSAGE()
        RAISERROR(50001,@V_ERROR_SEVERITY,@V_ERROR_STATE,@V_ERROR_STATE,@V_ERROR_MESSAGE)        
    END CATCH  
END

GO
