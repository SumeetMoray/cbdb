package org.nearbyshops.communitylibrary.RESTEndpoint;

import org.nearbyshops.communitylibrary.DAOs.BookReviewDAO;
import org.nearbyshops.communitylibrary.DaggerComponentBuilder;
import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.Model.BookReview;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookEndpoint;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookReviewEndpoint;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/BookReview")
public class BookReviewResource {

//    BookReviewDAOPrepared bookReviewDAO;

    public BookReviewResource() {

        // Inject the dependencies using Dependency Injection
        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);
    }

    @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveBookReview(BookReview bookReview)
        {
//            int idOfInsertedRow = Globals.bookDAO.saveBook(book);

            int idOfInsertedRow = Globals.bookReviewDAO.saveBookReview(bookReview);

            bookReview.setBookReviewID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/BookReview/" + idOfInsertedRow))
                        .entity(bookReview)
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
        @Path("/{BookReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateItem(BookReview bookReview, @PathParam("BookReviewID")int bookReviewID)
        {

            bookReview.setBookReviewID(bookReviewID);

//            int rowCount = Globals.bookDAO.updateBook(book);

            int rowCount = Globals.bookReviewDAO.updateBookReview(bookReview);


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



        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateReviewsBulk(List<BookReview> bookReviewsList)
        {
            int rowCountSum = 0;

            for(BookReview item : bookReviewsList)
            {
                rowCountSum = rowCountSum + Globals.bookReviewDAO.updateBookReview(item);
            }

            if(rowCountSum ==  bookReviewsList.size())
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            else if( rowCountSum < bookReviewsList.size() && rowCountSum > 0)
            {
                Response response = Response.status(Response.Status.PARTIAL_CONTENT)
                        .entity(null)
                        .build();

                return response;
            }
            else if(rowCountSum == 0 ) {

                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }


        @DELETE
        @Path("/{BookReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@PathParam("BookReviewID")int bookReviewID)
        {

            int rowCount = Globals.bookReviewDAO.deleteBookReview(bookReviewID);

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
        public Response getBookReviews(
                @QueryParam("BookID")Integer bookID,
                @QueryParam("MemberID")Integer memberID,
                @QueryParam("GetMember")Boolean getMember,
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

            BookReviewEndpoint endPoint = Globals.bookReviewDAO.getEndPointMetadata(bookID,memberID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<BookReview> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        Globals.bookReviewDAO.getBookReviews(
                                bookID,memberID,
                                sortBy,set_limit,set_offset
                        );


                if(getMember!=null && getMember)
                {
                    for(BookReview bookReview: list)
                    {
                        bookReview.setRt_member_profile(Globals.memberDAO.getMember(bookReview.getMemberID()));
                    }
                }

                endPoint.setResults(list);
            }


            //Marker

            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }





        @GET
        @Path("/{BookReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("BookReviewID")int bookReviewID)
        {
            System.out.println("BookReviewID=" + bookReviewID);


            //marker

            BookReview item = Globals.bookReviewDAO.getBookReview(bookReviewID);

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
