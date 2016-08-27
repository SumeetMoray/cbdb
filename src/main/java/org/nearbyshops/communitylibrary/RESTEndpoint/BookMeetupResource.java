package org.nearbyshops.communitylibrary.RESTEndpoint;

import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.Model.BookMeetup;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookEndpoint;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookMeetupEndpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/BookMeetup")
public class BookMeetupResource {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveBookMeetup(BookMeetup bookMeetup)
        {
            int idOfInsertedRow = Globals.bookMeetupDAO.saveBookMeetup(bookMeetup);

            bookMeetup.setBookMeetupID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/BookMeetup/" + idOfInsertedRow))
                        .entity(bookMeetup)
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
        @Path("/{BookMeetupID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateItem(BookMeetup bookMeetup, @PathParam("BookMeetupID")int bookMeetupID)
        {

            bookMeetup.setBookMeetupID(bookMeetupID);

            int rowCount = Globals.bookMeetupDAO.updateBookMeetup(bookMeetup);

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
        @Path("/{BookID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@PathParam("BookID")int bookMeetupID)
        {

            int rowCount = Globals.bookMeetupDAO.deleteBookMeetp(bookMeetupID);

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
        public Response getBookMeetup(
                @QueryParam("MemberLongitude") Double memberLongitude,
                @QueryParam("MemberLatitude") Double memberLatitude,
                @QueryParam("ProximityLimit") Double proximityMaximum,
                @QueryParam("SearchString") String stringString,
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

//            BookEndpoint endPoint = Globals.bookDAO.getEndPointMetadata(bookCategoryID,favouriteBookMemberID);

            BookMeetupEndpoint endPoint = Globals.bookMeetupDAO.getEndPointMetadata(memberLongitude,memberLatitude,
                    proximityMaximum);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<BookMeetup> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        Globals.bookMeetupDAO.getBookMeetup(
                                memberLongitude,memberLatitude,proximityMaximum,stringString,
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
        @Path("/{BookID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("BookID")int bookID)
        {
            System.out.println("BookID=" + bookID);


            //marker

            BookMeetup item = Globals.bookMeetupDAO.getBookMeetup(bookID);

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

}
