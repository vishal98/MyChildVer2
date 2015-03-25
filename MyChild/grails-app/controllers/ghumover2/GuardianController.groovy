package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_PARENT'])
class GuardianController extends RestfulController
{

    static responseFormats = ['json', 'xml']

    GuardianController() {
        super(Guardian)
    }


    def getAccountInfo()
    {
         def id = params.id.toString()


         JSON.use('ParentAccInfo')
                {
                  Guardian  response =   (id.isNumber()) ? Guardian.findById(id) : Guardian.findByUsername(id);
                  render response as JSON

                }


    }

     def getAllChildren()
     {
         def response = [:]
             try {
                          def id = params.id
                          def output = [:]
                          Guardian  g =   (id.isNumber()) ? Guardian.findById(id) : Guardian.findByUsername(id);
                          def children = g.getChildren()
                          output['numberOfChildren'] = children.size()
                       JSON.use('getChildren')
                          {
                               output['children'] = children
                              render output as JSON
                          }



                 }
            catch (Exception e)
            {
                response['status'] = "error"
                response['message'] = e
                render response as JSON

            }
     }


}