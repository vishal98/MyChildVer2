package ghumover2

import grails.converters.JSON


class ConversationController {
    def springSecurityService

    User user

    static responseFormats = ['json', 'xml']


    def getUserConversations()
    {
        def output = [:]
        try {

            user  =    (params.userId)? ( (params.userId.isNumber()) ? (User.findById(Long.parseLong(params.userId))) : User.findByUsername(params.userId) )   : user;

            JSON.use('msgList'){
                output ['numberOfConversations'] = user.conversations.size()
                output ['conversations'] =  user.conversations
                render output as JSON
            }






        }
        catch (NullPointerException ne)
        {
            output['status'] = "error"
            output['message'] = "User Id "+ params.userId +" not found"
            output['data'] = "NULL"
            render output as JSON
        }
        catch (Exception e)
        {
            output['status'] = "error"
            output['message'] = "Error occured "
            output['data'] = e
            render output as JSON
        }
    }













    def getCurUserConversations()
    {
        user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
        if (user) {
            getUserConversations()

        }
        else
        {
            def output = [:]
            output['status'] = "error"
            output['message'] = "Not logged in"
            output['data'] = "NULL"
            render output as JSON

        }

    }


    def getConversationFromUser()
    {
        def output = [:]
        try{
            user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
            User fromUser =  (params.userId)? ( (params.userId.isNumber()) ? (User.findById(Long.parseLong(params.userId))) : User.findByUsername(params.userId) )   : null;

            def conv = Conversation.findAllByFromIdAndToId(fromUser.username,user.username)
            JSON.use('msgList'){
                output ['numberOfConversations'] = conv.size()
                output ['conversations'] =  conv
                render output as JSON
            }
        }
        catch (NullPointerException ne)
        {
            output['status'] = "error"
            output['message'] = "User Id "+ params.userId +" not found"
            output['data'] = "NULL"
            render output as JSON
        }
        catch (Exception e)
        {
            output['status'] = "error"
            output['message'] = "User Id "+ params.userId +" not found"
            output['data'] = "NULL"
            render output as JSON

        }
    }





    def getConversationFromAndTo()
    {
        def output = [:]
        try{
            User toUser =  (params.toId)? ( (params.toId.isNumber()) ? (User.findById(Long.parseLong(params.toId))) : User.findByUsername(params.toId) )   : null;
            User fromUser =  (params.fromId)? ( (params.fromId.isNumber()) ? (User.findById(Long.parseLong(params.fromId))) : User.findByUsername(params.fromId) )   : null;

            def conv = Conversation.findAllByFromIdAndToId(fromUser.username,toUser.username)
            JSON.use('msgList'){
                output ['numberOfConversations'] = conv.size()
                output ['conversations'] =  conv
                render output as JSON
            }
        }
        catch (NullPointerException ne)
        {
            output['status'] = "error"
            output['message'] = "User Id "+ params.userId +" not found"
            output['data'] = "NULL"
            render output as JSON
        }
        catch (Exception e)
        {
            output['status'] = "error"
            output['message'] = "Some error occured"
            output['data'] = "NULL"
            render output as JSON

        }

    }







    def saveMessage()
    {
        def output = [:]
        try{


            User toUser =  (params.toId)? ( (params.toId.isNumber()) ? (User.findById(Long.parseLong(params.toId))) : User.findByUsername(params.toId) )   : null;
            User fromUser =  (params.fromId)? ( (params.fromId.isNumber()) ? (User.findById(Long.parseLong(params.fromId))) : User.findByUsername(params.fromId) )   : null;
            Long threadId = Long.parseLong(params.threadId)
            String msg = params.messageText
            Conversation conversation = Conversation.findByThreadId(threadId)
            conversation.addToMessages(new Message(messageText: msg , messageTime: new Date() , fromId: fromUser.username , toId: toUser.username)).save()
            render "ok"

        }
        catch (NullPointerException ne)
        {
            output['status'] = "error"
            output['message'] = "User Id  '"+ params.toId +"'  or  '"+params.fromId+"' not found. check Ids"
            output['data'] = "NULL"
            render output as JSON
        }
        catch (Exception e)
        {
            output['status'] = "error"
            output['message'] = "Some error occured"
            output['data'] = "NULL"
            render output as JSON

        }

    }














}
