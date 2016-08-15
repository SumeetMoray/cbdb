package org.nearbyshops.communitylibrary.RESTEndpoint;

import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.Model.BookCategory;
import org.nearbyshops.communitylibrary.Model.Member;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookCategoryEndpoint;
import org.nearbyshops.communitylibrary.ModelEndpoint.MemberEndpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/Member")
public class MemberResource {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveMember(Member member)
        {
            int idOfInsertedRow = Globals.memberDAO.saveMember(member);

            member.setMemberID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/v1/Member/" + idOfInsertedRow))
                        .entity(member)
                        .build();

                return response;

            }else if(idOfInsertedRow <= 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }


            return null;
        }


        @PUT
        @Path("/{MemberID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateMember(Member member, @PathParam("MemberID")int memberID)
        {

            member.setMemberID(memberID);

            int rowCount = Globals.memberDAO.updateMember(member);


            if(rowCount >= 1)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;

        }



        @DELETE
        @Path("/{MemberID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteMember(@PathParam("MemberID")int memberID)
        {

            int rowCount = Globals.memberDAO.deleteMember(memberID);

            if(rowCount>=1)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }

            if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getMember(
                @QueryParam("SortBy") String sortBy,
                @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                @QueryParam("metadata_only")Boolean metaonly)
        {

            int set_limit = 30;
            int set_offset = 0;
            final int max_limit = 100;

            if(limit!= null)
            {

                if (limit >= max_limit) {

                    set_limit = max_limit;
                }
                else
                {

                    set_limit = limit;
                }

            }

            if(offset!=null)
            {
                set_offset = offset;
            }

            MemberEndpoint endPoint = Globals.memberDAO.getEndPointMetadata();

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<Member> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        Globals.memberDAO.getMembers(
                                sortBy,set_limit,set_offset
                        );

                endPoint.setResults(list);
            }


            //Marker

            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }





        @GET
        @Path("/{MemberID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getMember(@PathParam("MemberID")int memberID)
        {

            //marker

            Member item = Globals.memberDAO.getMember(memberID);

            if(item!= null)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(item)
                        .build();

                return response;

            } else
            {

                Response response = Response.status(Response.Status.NO_CONTENT)
                        .entity(item)
                        .build();

                return response;

            }
        }



    @GET
    @Path("/Validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateDistributor(@QueryParam("Password")String password,@QueryParam("Username")String userName,@QueryParam("ID")Integer id)
    {

        boolean isValid = false;

        Member tempEndUser = null;

        if(id!=null)
        {
            tempEndUser = Globals.memberDAO.getMemberPassword(id,null);

            System.out.println(id + " : " + userName);

        }else if(userName !=null)
        {
            tempEndUser = Globals.memberDAO.getMemberPassword(null,userName);
        }


        if(tempEndUser!=null && tempEndUser.getPassword()!=null && tempEndUser.getPassword().equals(password))
        {
            isValid = true;

            tempEndUser.setPassword(null);

        }



        if(isValid)
        {
            Response response = Response.status(Response.Status.OK)
                    .entity(tempEndUser)
                    .build();

            return response;

        } else
        {

            Response response = Response.status(Response.Status.UNAUTHORIZED)
                    .entity(null)
                    .build();

            return response;

        }

    }


}
